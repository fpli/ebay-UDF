package com.ebay.cassini.log.realtime.query_helpers

import com.ebay.cassini.log.realtime.Logger
import com.ebay.cassini.query.api.expression.{ QueryExpression, QueryExpressionDsbeUsecase, QueryExpressionRawUserQuery, QueryExpressionMacro }
import com.ebay.cassini.query.api.core.SingleQuery
import org.apache.commons.codec.binary.{ Base64, StringUtils }
import org.codehaus.jackson.map.ObjectMapper
import scala.annotation.tailrec
import scala.util.{ Failure, Success, Try }
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
 * This class has the following responsibilities.
 */

/*
 * NOTE: this is a copy from 
 * https://github.corp.ebay.com/SBE-SRE/cassini-realtime/blob/master/utils/src/main/scala/com/ebay/cassini/log/realtime/query_helpers/QueryBlobAnalyzer.scala
 * The original authors were from SBE.
 */
object QueryBlobAnalyzer extends Logger {
    private val allTables = List("active", "completed", "dsbe", "sibe", "product", "catalog")
}

case class QueryMacro(macroName: String, macroValue: String)

case class QueryBlobAnalyzer(blob: Array[Byte]) extends Logger {
    private lazy val unescapedBlob = Utils.unEscapeSpecialCharacters(blob)
    private lazy val requestProcessor = RequestProcessorImpl(unescapedBlob)

    private lazy val queryStatistics = {
        QueryServiceManager.api.populateRequest(requestProcessor)
        requestProcessor.cassiniQueryStatistics
    }

    lazy val base64 = StringUtils.newStringUtf8(Base64.encodeBase64(unescapedBlob, false, false))

    def getSiteId = {
        collectFirstValueOnTable { i =>
            val singleQuery = getSingleQuery(i)
            singleQuery.get.getProperty("site_id")
        }
    }

    def getDsbeUsecases: List[String] = {
        val usecases = new ListBuffer[String]()
        queryStatistics.filter(_.queryContainer.size() > 0).foreach(x =>
            (0 until x.queryContainer.size()).foreach(i => {
                val singleQuery = x.queryContainer.getQueryAndFlags(i).getQuery
                val tblName = singleQuery.getPrimaryInputTable
                if (tblName.contains("dsbe")) {
                    if (singleQuery.getSelectionCriteria.isInstanceOf[QueryExpressionDsbeUsecase]) {
                        val usecaseStr = singleQuery.getSelectionCriteria.asInstanceOf[QueryExpressionDsbeUsecase].getUsecase
                        log.warn("usecase is " + usecaseStr)
                        if (usecaseStr != "()") {
                            usecases.append(usecaseStr)
                        }
                    }
                }
            }))

        usecases.toList
    }

    def getTreatmentName = {
        queryStatistics.map(_.queryContainer.getProperty("treatment_name"))
    }

    def getSelectionCriteria = {
        queryStatistics.filter(_.queryContainer.size() > 0).flatMap { x =>
            Try {
                val objSingleQuery = x.queryContainer.getQueryAndFlags(0).getQuery
                objSingleQuery.getSelectionCriteria.toString
            }.toOption
        }
    }

    def getProfile = {
        queryStatistics.filter(_.queryContainer.size() > 0).flatMap { x =>
            Try(x.queryContainer.getQueryAndFlags(0).getQuery.getProfileName).toOption
        }
    }

    def getAllTableNames = {
        queryStatistics.filter(_.queryContainer.size() > 0).flatMap { x =>
            Try {
                val tableNameList = (0 until x.queryContainer.size()).map(i => x.queryContainer.getQueryAndFlags(i).getQuery.getPrimaryInputTable)
                tableNameList.toSet.mkString(",").stripPrefix(",").stripSuffix(",").trim
            }.toOption
        }
    }

    def getPLSValue = {
        queryStatistics.filter(_.queryContainer.size() > 0).flatMap { a =>
            Option(a.queryContainer.getQueryAndFlags(0).getQuery.getProperty("enable_profile_lookup"))
        }
    }

    def getDependentFields = {
        queryStatistics.map(_.queryStatistics.getDependentFields.asScala.toList)
    }

    def getUseCase = {
        Try {
            queryStatistics.get.queryContainer.getProperty("fisng.X-EBAY-SOA-USECASE-NAME")
        }.toOption
    }

    def getRawUserQuery: Option[String] = {
        Try {
            queryStatistics.filter(_.queryContainer.size() > 0).flatMap { x =>
                val singleQuery = x.queryContainer.getQueryAndFlags(0).getQuery
                findRawUserQuery(List(singleQuery.getSelectionCriteria))
            }
        }.toOption.getOrElse(None)
    }

    def getQueryProperties: Option[Map[String, (Int, String)]] = {
        Try {
            queryStatistics.filter(_.queryContainer.size() > 0).map(a => {
                (0 until a.queryContainer.size()).flatMap(i => {
                    val singleQuery = a.queryContainer.getQueryAndFlags(i).getQuery
                    singleQuery.getProperties.asScala.toList.map(p => (p -> (i, singleQuery.getProperty(p))))
                }).toMap
            })
        }.toOption.getOrElse(None)
    }

    def getQueryContainerProperties: Option[Map[String, String]] = {
        Try {
            queryStatistics.filter(_.queryContainer.size() > 0).map(qs => {
                qs.queryContainer.getProperties.asScala.toList.map(p => (p -> qs.queryContainer.getProperty(p))).toMap
            })
        }.toOption.getOrElse(None)
    }

    def getDSBEQueryDetails: List[(String, Option[String])] = {
        queryStatistics.filter(_.queryContainer.size() > 0).map(x => {
            (0 until x.queryContainer.size()).map(i => {
                val singleQuery = x.queryContainer.getQueryAndFlags(i).getQuery
                val tblName = singleQuery.getPrimaryInputTable
                val usecase = {
                    if (singleQuery.getSelectionCriteria.isInstanceOf[QueryExpressionDsbeUsecase]) {
                        Option(singleQuery.getSelectionCriteria.asInstanceOf[QueryExpressionDsbeUsecase].getUsecase)
                    }
                    else {
                        None
                    }
                }
                (tblName, usecase)
            })
        }).getOrElse(List()).toList
    }

    def getTrackerGuids: List[String] = {
        getSingleQueries.map(singleQuery => {
            singleQuery.getQueryTracker.getGuid
        })
    }

    def getMacroInSelectionCriteria: List[(String, String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val macros = getMacros(List(singleQuery.getSelectionCriteria), List[QueryMacro]())
                macros.map(m => (m.macroName, m.macroValue, i))
            }
        })
    }

    def getOperatorInSelectionCriteria: List[(String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val operators = getOperatorNames(List(singleQuery.getSelectionCriteria), Set[String]())
                operators.map(m => (m, i))
            }
        })
    }

    def getMacroInNamedExpression: List[(String, String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val namedExpressions = getNamedExpressions(singleQuery)
                val macros = namedExpressions.flatMap(e => getMacros(List(e), List[QueryMacro]()))
                macros.map(m => (m.macroName, m.macroValue, i))
            }
        })
    }

    def getOperatorInNamedExpression: List[(String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val namedExpressions = getNamedExpressions(singleQuery)
                val operators = namedExpressions.flatMap(e => getOperatorNames(List(e), Set[String]()))
                operators.map(m => (m, i))
            }
        })
    }

    def getMacroInOutputFields: List[(String, String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val outputFieldExpression = getOutputFieldExpression(singleQuery)
                val macros = outputFieldExpression.flatMap(e => getMacros(List(e), List[QueryMacro]()))
                macros.map(m => (m.macroName, m.macroValue, i))
            }
        })
    }

    def getOperatorInOutputFields: List[(String, Int)] = {
        getSingleQueries.zipWithIndex.flatMap({
            case (singleQuery, i) => {
                val outputFieldExpression = getOutputFieldExpression(singleQuery)
                val macros = outputFieldExpression.flatMap(e => getOperatorNames(List(e), Set[String]()))
                macros.map(m => (m, i))
            }
        })
    }

    def getBlenderNames: List[String] = {
        val blenders = queryStatistics.filter(_.queryContainer.size() > 0)
            .flatMap(qs => Option(qs.queryContainer.getProperty("Blenders")))
            .getOrElse("{}")

        val parseResult = Try { new ObjectMapper().readTree(blenders) }

        parseResult match {
            case Success(jsonNode) => {
                val blenderJson = jsonNode.get("blenders")
                if (Option(blenderJson).isEmpty) {
                    return List[String]()
                }
                val iterator = blenderJson.getElements.asScala
                iterator.foldLeft(List[String]()) {
                    (acc, jsonNode) =>
                        {
                            val name = jsonNode.findValue("name").getTextValue
                            name :: acc
                        }
                }
            }
            case Failure(exception) => {
                List[String]()
            }
        }

    }

    private def getSingleQuery(index: Int) = {
        val queryAndFlags = queryStatistics.map { stats => stats.queryContainer.getQueryAndFlags(index) }
        queryAndFlags.map(_.getQuery)
    }

    private def getSingleQueries: List[SingleQuery] = {
        queryStatistics.filter(_.queryContainer.size() > 0).map(x => {
            (0 until x.queryContainer.size()).map(i => x.queryContainer.getQueryAndFlags(i).getQuery)
        }).getOrElse(List[SingleQuery]()).toList
    }

    private def getPrimaryTable(index: Int) = {
        val singleQuery = getSingleQuery(index)
        singleQuery.map(_.getPrimaryInputTable)
    }

    private def containsNecessaryTables(index: Int): Boolean = {
        def containsKnownTables(tableName: String): Boolean = {
            QueryBlobAnalyzer.allTables.collectFirst {
                case a if tableName.toLowerCase.contains(a) => a
            }.isDefined
        }

        val table = getPrimaryTable(index)
        table match {
            case Some(a) if containsKnownTables(a) => true
            case _ => false
        }
    }

    private def collectFirstValueOnTable[T](f: Int => T) = {
        val size = queryStatistics.flatMap { stats =>
            val size = stats.queryContainer.size()
            0 until size collectFirst {
                case i if containsNecessaryTables(i) =>
                    f(i)
            }
        }
        size
    }

    private def getNamedExpressions(singleQuery: SingleQuery): List[QueryExpression] = {
        val count = singleQuery.getNamedExpressionCount
        val operands = (0 until count).map(c => {
            val namedExpression = singleQuery.getNamedExpression(c)
            namedExpression.getBoundExpression
        }).toList
        operands
    }

    private def getOutputFieldExpression(singleQuery: SingleQuery): List[QueryExpression] = {
        val count = singleQuery.getOutputFieldCount
        val expressions = (0 until count).map(c => {
            val outputField = singleQuery.getOutputField(c).getBoundExpression()
            outputField
        }).toList
        expressions
    }

    @tailrec
    private def getMacros(queryExpressions: List[QueryExpression], macros: List[QueryMacro]): List[QueryMacro] = {
        queryExpressions match {
            case Nil => macros
            case query :: rest =>
                if (Option(query).isEmpty) {
                    macros
                }
                else {
                    val children = (0 until query.getOperandCount).map(i => query.getOperand(i)).toList
                    val total = if (query.isInstanceOf[QueryExpressionMacro]) {
                        val macroExpression = query.asInstanceOf[QueryExpressionMacro]
                        val macroValue = (0 until macroExpression.getOperandCount).map(i => macroExpression.getOperand(i).toString()).mkString(",")
                        macros ++ List(QueryMacro(macroExpression.getMacroName, macroValue))
                    }
                    else {
                        macros
                    }
                    getMacros(children ++ rest, total)
                }
        }
    }

    @tailrec
    private def getOperatorNames(queryExpressions: List[QueryExpression], operators: Set[String]): Set[String] = {
        queryExpressions match {
            case Nil => operators
            case query :: rest =>
                if (Option(query).isEmpty) {
                    operators
                }
                else {
                    val children = (0 until query.getOperandCount).map(i => query.getOperand(i)).toList
                    val operator = query.getOperator
                    val total = if (Option(operator).isEmpty) operators else operators + operator.getName
                    getOperatorNames(children ++ rest, total)
                }
        }
    }

    @tailrec
    private def findRawUserQuery(nodeList: List[QueryExpression]): Option[String] = {
        nodeList match {
            case Nil => None
            case query :: rest =>
                if (Option(query).isEmpty) {
                    None
                }
                else if (query.isInstanceOf[QueryExpressionRawUserQuery]) {
                    Option(query.asInstanceOf[QueryExpressionRawUserQuery].getRawUserQuery)
                }
                else {
                    val children = (0 until query.getOperandCount).map(i => query.getOperand(i)).toList
                    findRawUserQuery(children ++ rest)
                }
        }
    }
}


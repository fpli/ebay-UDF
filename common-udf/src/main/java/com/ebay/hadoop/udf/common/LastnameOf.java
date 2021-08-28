package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.common.utils.StrUtils;
import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by szang on 7/24/17.
 *
 * This UDF takes a name String as input and returns the corresponding lastname as output.
 *
 * The logic is from dw_user.stg_user_pii_n_w.xfr.
 *
 * Why need this UDF?
 * User_info_pii data sourced from Oracle only includes "name" as a column.
 * In TD pipeline, dw_user.stg_user_pii_n_w.xfr was applied to generate additional "Firstname" and "Lastname" columns for downstream STT convenience.
 * In Spark pipeline, this UDF will be used to do the same thing so that the logics can match at each stage.
 */
@ETLUdf(name = "lastnameOf")
public class LastnameOf extends UDF {

    public String evaluate(String name) {
        return evaluate(name, "\\s+");
    }

    public String evaluate(String name, String del) {

        // Since the null check is not implemented in the following dw_user.stg_user_pii_n_w.xfr, meaning in.name will never be null in reality.
        // Leave the check clause here for the sake of logical completeness.
        if (name == null || del == null) return null;
        
        String temp = StrUtils.trim(name);
        if (temp.equals("")) return "";

        char backspace = '\u0008';
        char space = ' ';

        // When input name is an empty String, the split result is [""] and the empty String will be returned in the next line.
        //Only trim space instead of all whitespace chars
        String[] split = temp.split(del);

        if (split.length <= 2) {
            return StrUtils.trim(split[split.length - 1].replace(backspace, space));
        }

        String lastname = split[split.length - 1];
        String lastname_up = lastname.toUpperCase();
        if (!lastname_up.matches("^JR$|^SR$|^II$|^III$|^IV$|^V$|^VI$|^VII$|^VIII$|^IX$")) {
            return StrUtils.trim(lastname.replace(backspace, space));
        }

        // If any of the above regex matches the last split, return the second to the last
        return StrUtils.trim(split[split.length - 2].replace(backspace, space));
    }
}


/* dw_user.stg_user_pii_n_w.xfr

out::reformat(in) =
begin
    let string(unsigned integer(1)) firstname;
    let string(unsigned integer(1)) lastname;
    let string(unsigned integer(1)) prelastname;
    let string(unsigned integer(1)) tempname;
    let string(unsigned integer(1)) lastname_up;
    let string(1) char8 = "\x08";
    let int len1;
    let int len2;
    let int length;
    let int lidx;
    let int ridx;
    tempname = string_substring(in.name,1,64);
    tempname = string_lrtrim(tempname);
    len1 = string_length(tempname);
    len2 = string_length(string_replace(tempname," ", ""));
    length = len1 - len2;
    if (length == 0 )
    begin
         firstname = tempname;
         lastname = tempname;
    end
    else
    begin
         ridx = string_rindex(tempname," ");
         lidx = string_index(tempname," ");
         firstname = string_substring(tempname,1, lidx-1);
         lastname = string_substring(tempname,ridx+1, len1-ridx);
         lastname_up = string_upcase(lastname);
    end

    if (length  >= 2)
    begin
            if (re_index(lastname_up,"^JR$|^SR$|^II$|^III$|^IV$|^V$|^VI$|^VII$|^VIII$|^IX$") > 0 )
            begin
                prelastname = string_substring(tempname, 1, ridx-1);
                ridx = string_rindex(prelastname," ");
                len1 = string_length(prelastname);
                prelastname = string_substring(prelastname,ridx+1, len1-ridx);
                lastname = prelastname;
            end
    end
    firstname = string_lrtrim(string_replace(firstname, char8, " "));
    lastname = string_lrtrim(string_replace(lastname, char8, " "));
    out.* :: in.*;
    out.name :: string_replace(string_substring(in.name, 1, 64), char8, " ");
    out.city :: string_substring(in.city, 1, 64);
    out.state :: string_substring(in.state, 1, 64);
    out.zip :: string_substring(in.zip, 1, 24);
    out.dayphone :: string_substring(in.dayphone, 1, 32);
    out.nightphone :: string_substring(in.nightphone, 1, 32);
    out.faxphone :: string_substring(in.faxphone, 1, 32);
    out.email :: string_substring(in.email, 1, 64);
    out.address_alt :: string_substring(in.address_alt, 1, 64);
    out.firstname :: firstname;
    out.lastname :: lastname;
end;
* */
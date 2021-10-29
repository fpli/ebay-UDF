# ebay-UDF

Several requirements
1. No external dependency 
2. No system.out
3. Unit test

Release process
1. Create the PR and get initial approval
2. Release in corresponding test db and hermes will do the integration test
3. Make self-service udf testing on prod env, https://wiki.vip.corp.ebay.com/display/~fwang12/Self+service+UDF+testing
4. Merge the PR and release in production

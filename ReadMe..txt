Instruction to Run
1. Modify the content of ".\src\main\resources\TestDriver.txt"
2. Modify the default value "sample2025" using replaceAll with anydifferent value
3. 

Note - Things yet to be done
-Currently the script can handle only pull and put requests.
-Things yet to be done - handle requests query param, update custom reports
-Hard coded csv path in DataProvider.java line 54 should be updated (It should be changed before running) - To be set as environment variable during CI/CD pipeline setup

CSV Description (Runner File)

Execute - Y or N (Determines if the etst data is to be run or ignored)
URI - The uri as in the api documentation
requestMethod - POST,PUT
JSONData1 - to be written in <<key>>|<<valuepair> as given in json data
JSONData2 -  to be written in <<key>>|<<valuepair> as given in json data
JSONData3 - to be written in <<key>>|<<valuepair> as given in json data
expectedRespCode - The expected response code . This value shall be used to assert

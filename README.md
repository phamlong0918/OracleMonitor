Step 1: Start docker DB, mountebank before run app and wait for oracle, mysql start

docker-compose -f docker-compose.oracle.yml up -d && docker-compose -f docker-compose.mysql.yml up -d
docker-compose -f docker-compose.mountebank.yml up -d

Step 2: Build project
Step 3: Call curl

curl --location --request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    helloWorld\n}","variables":{}}'

curl --location --request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    profile(hackathonId: \"0001100129\") {\n        userInfo {\n            fullName\n        }\n        bankAccountInfo {\n            userId\n            bankAccountNo\n            bankCode\n            bankAccountName\n        }\n    }\n}","variables":{}}'

MySQL + Oracle + Third party with full field ============================================================

curl --location --request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    profiles(name: \"n\") {\n        userInfo {\n        userName\n        email\n        firstName\n        lastName\n        gender\n        birthDay\n        relationShip\n        status\n        honorific\n        phone\n        hackathonId\n        vsdStatus\n        optChannel\n        cusType\n        transferStatus\n        nameAcronyn\n        mst\n        flowOpenAccount\n        sysUserType\n        systemUser\n        envelopeId\n        docusignStatus\n        accountStatus\n        avatarData\n        avatarHeader\n        firstTimeLogin\n        editTable\n        bpmEkycStatus\n        bpmEkycDenyReason\n        bpmEkycDenyContent\n        onboardingData\n        clientKey\n        contractPayload\n        hasPermBondPt\n        subFlowOpenAccount\n        isForeignPhone\n        avatarUrl\n        signCloseContract\n        phoneCode\n        kycLevel\n        openSource\n        homePhone\n        }\n        bankAccountInfo {\n            bankAccountNo\n            bankCode\n            bankAccountName\n            hackathonId\n            bankName\n            bankBranch\n            bankProvince\n            idNumber\n            idName\n            idDate\n            toPlace\n            status\n            maxLimit\n            currentLimit\n            cashAccountType\n        }\n        addressInfo {\n            hackathonId\n            address\n            division1\n            division2\n            division3\n            division4\n            division5\n            division6\n            addressType\n            originalId\n            permanentAddress\n        }\n    }\n}","variables":{}}'
============================================================
CURL with batch mapping

curl --location --request POST 'http://localhost:8080/graphql' \
--header 'content-type: application/json' \
--data-raw '{
    "query": "query {\n  allUsers {\n    userName\n    firstName\n    lastName\n    gender\n    birthDay\n    relationShip\n    status\n    email\n    phone\n    identifications {\n      idType\n      idNumber\n      issueDate\n      issuePlace\n    }\n  }\n}"
}'

=========================================================================================
scalar date/time type

DateTime
    An RFC-3339 compliant date time scalar that accepts string values like 1996-12-19T16:39:57-08:00 and produces java.time.OffsetDateTime objects at runtime
Time
    An RFC-3339 compliant time scalar that accepts string values like 16:39:57-08:00 and produces java.time.OffsetTime objects at runtime
LocalTime
    24-hour clock time string in the format hh:mm:ss.sss or hh:mm:ss if partial seconds is zero and produces java.time.LocalTime objects at runtime.
Date
    An RFC-3339 compliant date scalar that accepts string values like 1996-12-19 and produces java.time.LocalDate objects at runtime

echo 'find companies by request json'
curl --header "Content-Type: application/json" --request PUT --data '{"n": 1,  "name": "COMPANY_1"}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# / answer: [{"n":1,"name":"COMPANY_1"}]
echo -e "\n"

echo 'find companies by name LIKE COMPANY'
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY"}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# answer: [{"n":1,"name":"COMPANY_1"},{"n":2,"name":"COMPANY_2"},{"n":3,"name":"3_COMPANY"}]
echo -e "\n"

echo 'find companies by name EQUAL COMPANY_2'
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY_2"}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# answer:  [{"n":2,"name":"COMPANY_2"}]
echo -e "\n"

echo 'find companies by name LIKE %2%'
curl --header "Content-Type: application/json" --request PUT --data '{"name": "2"}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# answer: [{"n":2,"name":"COMPANY_2"}]
echo -e "\n"

echo 'find companies by name LIKE COMPANY'
echo 'PUT http://127.0.0.1:8980/vacancy/api/company/find/'
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY"}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# answer: [{"n":1,"name":"COMPANY_1"},{"n":2,"name":"COMPANY_2"},{"n":3,"name":"3_COMPANY"}]
echo -e "\n"

echo 'find company by N'
curl --header "Content-Type: application/json" --request PUT --data '{"n" : 1}' 'http://127.0.0.1:8980/vacancy/api/company/find/' | jq
# [{"n":1,"name":"COMPANY_1"}
echo -e "\n"

echo 'get ALL companies sorted by column n'
echo 'http://127.0.0.1:8980/vacancy/api/company/sortByColumn/n'
curl --request GET 'http://127.0.0.1:8980/vacancy/api/company/sortByColumn/n' | jq
echo -e "\n"

echo 'get ALL companies sorted by column name'
echo 'GET http://127.0.0.1:8980/vacancy/api/company/sortByColumn/name'
curl --request GET 'http://127.0.0.1:8980/vacancy/api/company/sortByColumn/name' | jq
echo -e "\n"

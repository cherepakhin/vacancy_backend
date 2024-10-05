// find companies by N AND name in  request jsom
curl --header "Content-Type: application/json" --request PUT --data '{"n": 1,  "name": "COMPANY_1"}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
// answer: [{"n":1,"name":"COMPANY_1"}]

// find companies by LIKE name
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY"}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
// answer: [{"n":1,"name":"COMPANY_1"},{"n":2,"name":"COMPANY_2"},{"n":3,"name":"3_COMPANY"}]

// find companies by EQUAL name
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY_2"}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
// answer:  [{"n":2,"name":"COMPANY_2"}]

// find companies by LIKE name
curl --header "Content-Type: application/json" --request PUT --data '{"name": "2"}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
//answer: [{"n":2,"name":"COMPANY_2"}]

// find companies by LIKE name
curl --header "Content-Type: application/json" --request PUT --data '{"name": "COMPANY"}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
//answer: [{"n":1,"name":"COMPANY_1"},{"n":2,"name":"COMPANY_2"},{"n":3,"name":"3_COMPANY"}]

// find company by N
curl --header "Content-Type: application/json" --request PUT --data '{"n" : 1}' 'http://127.0.0.1:8980/vacancy/api/company/find/'
[{"n":1,"name":"COMPANY_1"}

// get ALL companies sorted by column n
curl --request GET  'http://127.0.0.1:8980/vacancy/api/company/sortByColumn/n'

// get ALL companies sorted by column name
curl --request GET  'http://127.0.0.1:8980/vacancy/api/company/sortByColumn/name'

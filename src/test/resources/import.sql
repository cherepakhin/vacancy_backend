delete from vacancy;
delete from contact;
delete from company;
COMMIT;

insert into company (n,name) values (-1,'-'), (1,'COMPANY_1'), (2,'COMPANY_2'), (3,'3_COMPANY');

insert into contact (n, company_n, name, email, phone, comment) values (-1, -1, '-', '-', '-', '-');
insert into contact (n, company_n, name, email, phone, comment) values
    (1, 1, 'CONTACT_1_COMPANY_1', 'CONTACT_1_EMAIL','CONTACT_1_PHONE','CONTACT_1_COMMENT'),
    (2, 1, 'CONTACT_2_COMPANY_1', 'CONTACT_2_COMPANY_1_EMAIL','CONTACT_2_COMPANY_1_PHONE','CONTACT_2_COMPANY_1_COMMENT'),
    (3, 2, 'CONTACT_3_COMPANY_2', 'CONTACT_3_EMAIL','CONTACT_3_PHONE','CONTACT_3_COMMENT'),
    (4, 3, 'CONTACT_4_COMPANY_3','CONTACT_3_EMAIL','CONTACT_3_PHONE','CONTACT_3_COMMENT');
COMMIT;

insert into vacancy (n, company_n, name, contact_n, comment) values
    (1, 1, 'NAME_VACANCY_1_COMPANY_1', 1, 'COMMENT_VACANCY_1_COMPANY_1'),
    (2, 1, 'NAME_VACANCY_2_COMPANY_1', 2, 'COMMENT_VACANCY_2_COMPANY_1'),
    (3, 2, 'NAME_VACANCY_1_COMPANY_2', 3, 'COMMENT_VACANCY_1_COMPANY_2'),
    (4, 3, 'NAME_VACANCY_1_COMPANY_3', 4, 'COMMENT_VACANCY_1_COMPANY_3');
COMMIT;


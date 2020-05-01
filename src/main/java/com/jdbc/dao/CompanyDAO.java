package com.jdbc.dao;

import com.jdbc.model.Company;

public interface CompanyDAO extends DataAccessObject<Company> {

    Company get(String companyName);
}

package com.jdbc.dao;

import com.jdbc.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public interface CustomerDAO extends DataAccessObject<Customer> {

    Customer get(String customerName);
}

package de.philipphauer.blog.misc.constructorinjection;

public class CustomerResource {

    private CustomerRepository repo;
    private CRMClient client;

    public CustomerResource(CustomerRepository repo, CRMClient client) {
        this.repo = repo;
        this.client = client;
    }
}
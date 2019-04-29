#!/usr/bin/env python3
import random
from datetime import datetime, timedelta

from bson import ObjectId
from faker import Faker
from pymongo import MongoClient

faker = Faker("en")

def seed():
    print("Start seeding...")
    client = MongoClient('mongodb://mongo:27017/test')
    db = client.test
    new_products = [generate_product() for _ in range(500)]
    db.products.delete_many({})
    db.products.insert_many(new_products)
    print("Finished seeding.")

def generate_product():
    return {
        "_id": ObjectId(),
        "name": faker.company(),
        "amount": random.randrange(100),
        "isActive": faker.boolean(chance_of_getting_true=80),
        "dateCreated": faker.date_time_between(start_date="-10y", end_date="now"),
        "tags": [faker.word(), faker.word()]
    }

if __name__ == '__main__':
    seed()

#!/usr/bin/env python3.6

import random
from typing import List

from bson import ObjectId
from faker import Faker
from pymongo import MongoClient

POSSIBLE_STATES = ['ACTIVE', 'INACTIVE']
POSSIBLE_TAGS = ['vacation', 'business', 'technology', 'mobility', 'apparel']
faker = Faker('en')


class MongoSeeder:

    def __init__(self):
        host = 'mongo' if script_runs_within_container() else 'localhost'
        client = MongoClient(f'mongodb://{host}:27017/test')
        self.db = client.test

    def seed(self):
        print('Clearing collection...')
        self.db.designs.remove({})
        print('Inserting new designs...')
        designs = [generate_design() for _ in range(100)]
        self.db.designs.insert_many(designs)
        print('Done.')


def generate_design():
    data = {
        '_id': ObjectId()
        , 'name': faker.word()
        , 'description': faker.sentence(nb_words=7)
        , 'date': faker.date_time()
        , 'tags': choose_max_n_times(possibilities=POSSIBLE_TAGS, max_n=3)
        , 'state': random.choice(POSSIBLE_STATES)
        , 'designer': {
            'id': random.randint(0, 999999)
            , 'name': faker.name()
            , 'address': faker.address()
        }
    }
    if faker.boolean(chance_of_getting_true=50):
        data['superDesign'] = True
    return data


def script_runs_within_container():
    with open('/proc/1/cgroup', 'r') as cgroup_file:
        return 'docker' in cgroup_file.read()


def choose_max_n_times(possibilities: List, max_n: int) -> List:
    copied_list = list(possibilities)
    random.shuffle(copied_list)
    n = random.randint(0, max_n)
    chosen = [copied_list.pop() for _ in range(n)]
    return chosen


MongoSeeder().seed()


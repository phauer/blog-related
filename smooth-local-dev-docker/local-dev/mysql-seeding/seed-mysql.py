#!/usr/bin/env python3.6
import random
import time

import mysql.connector
from faker import Faker
from mysql.connector import InterfaceError

POSSIBLE_STATES = ['ACTIVE', 'INACTIVE']
faker = Faker('en')


def main():
    MySqlSeeder().seed()


class MySqlSeeder:

    def __init__(self):
        config = {
            'user': 'root',
            'password': 'root',
            'host': 'mysql' if script_runs_within_container() else 'localhost',
            'port': '3306',
            'database': 'database'
        }
        while not hasattr(self, 'connection'):
            try:
                self.connection = mysql.connector.connect(**config)
                self.cursor = self.connection.cursor()
            except InterfaceError:
                print("MySQL Container has not started yet. Sleep and retry...")
                time.sleep(1)

    def seed(self):
        print("Clearing old data...")
        self.drop_user_table()
        print("Start seeding...")
        self.create_user_table()
        self.insert_users()

        self.connection.commit()
        self.cursor.close()
        self.connection.close()
        print("Done")

    def create_user_table(self):
        sql = '''
        CREATE TABLE users(
          id INT PRIMARY KEY AUTO_INCREMENT,
          name VARCHAR(50),
          state VARCHAR(50),
          birthday TIMESTAMP,
          notes VARCHAR(150),
          is_adult TINYINT(1)
        );
        '''
        self.cursor.execute(sql)

    def insert_users(self):
        for _ in range(300):
            sql = '''
            INSERT INTO users (name, state, birthday, notes, is_adult)
            VALUES (%(name)s, %(state)s, %(birthday)s, %(notes)s, %(is_adult)s);
            '''
            user_data = {
                'name': faker.name(),
                'state': random.choice(POSSIBLE_STATES),
                'birthday': faker.date_time(),
                'notes': faker.sentence(nb_words=5),
                'is_adult': faker.boolean(chance_of_getting_true=80)
            }
            self.cursor.execute(sql, user_data)

    def drop_user_table(self):
        self.cursor.execute('DROP TABLE IF EXISTS users;')


def script_runs_within_container():
    with open('/proc/1/cgroup', 'r') as cgroup_file:
        return 'docker' in cgroup_file.read()


if __name__ == '__main__':
    main()

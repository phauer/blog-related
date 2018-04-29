#!/usr/bin/env python3
import json

from faker import Faker
from flask import Flask, Response

app = Flask(__name__)
faker = Faker('en')


# A: Generate the payload with faker
@app.route('/users', methods=['GET'])
def get_users_faker():
    response_users = [generate_user(user_id) for user_id in range(50)]
    payload = {
        'users': response_users,
        'size': len(response_users)
    }
    return Response(json.dumps(payload), mimetype='application/json')


def generate_user(user_id):
    return {
        'id': user_id,
        'email': faker.email(),
        'name': faker.name(),
        'address': faker.address(),
        'company': faker.company(),
        'keyAccountInfo': faker.sentence(nb_words=6) if faker.boolean(chance_of_getting_true=50) else None
    }


# B: Return a static payload
@app.route('/users2', methods=['GET'])
def get_users_static():
    with open('static-user-response.json', 'r') as payload_file:
        return Response(payload_file.read(), mimetype='application/json')


app.run(debug=False, port=5000, host='0.0.0.0')

#!/usr/bin/env python3
import json
from flask import Flask, Response

app = Flask(__name__)


@app.route('/userProfiles/<path:user_id>')
def get_design_data(user_id):
    response = {
        "id": user_id,
        "name": "Peter",
        "avatarUrl": "http://localhost/peter.jpg"
    }
    return Response(json.dumps(response), mimetype='application/json')


if __name__ == '__main__':
    # nice: in debug mode, flask detects changes in this file. no need to restart the process!
    # 0.0.0.0 -> make server accessible externally (important for docker)
    app.run(debug=True, port=5000, host='0.0.0.0')

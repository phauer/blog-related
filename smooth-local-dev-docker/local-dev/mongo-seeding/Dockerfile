FROM python:3.6.4-alpine3.7

COPY . /

RUN pip install pipenv
RUN pipenv install --deploy --system

CMD python3 /seed-mongo.py

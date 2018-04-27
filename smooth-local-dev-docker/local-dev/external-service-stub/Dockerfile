FROM python:3.6.4-alpine3.7

RUN pip install pipenv

COPY Pipfile* /
RUN pipenv install --deploy --system

COPY external-service-stub.py /
COPY static-user-response.json /static-user-response.json
CMD python3 /external-service-stub.py

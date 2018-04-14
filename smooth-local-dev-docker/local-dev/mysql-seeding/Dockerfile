FROM python:3.6.4-alpine3.7

RUN pip install pipenv

COPY Pipfile* /
RUN pipenv install --deploy --system

COPY seed-mysql.py /
CMD python3 /seed-mysql.py

FROM python:3.7.2-alpine3.8

RUN pip install --ignore-installed "pymongo==3.7.2" "Faker==1.0.2"

COPY seedMongo.py /
CMD python3 /seedMongo.py

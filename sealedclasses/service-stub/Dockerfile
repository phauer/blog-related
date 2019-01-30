FROM python:3.6.4-alpine3.7

RUN pip install --ignore-installed "flask==1.0.1"

COPY service-stub.py /
CMD python3 /service-stub.py
FROM python:3.6.4-alpine3.7

RUN apk add --no-cache \
    build-base cairo-dev cairo cairo-tools \
    # pillow dependencies
    jpeg-dev zlib-dev freetype-dev lcms2-dev openjpeg-dev tiff-dev tk-dev tcl-dev

RUN pip install "flask==1.0.1" "CairoSVG==2.1.3"

COPY circle.svg /
COPY svg-converter-service.py /
CMD python3 /svg-converter-service.py
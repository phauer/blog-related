# Alpine Image with CairoSVG

```bash
# build and start the docker container
docker-compose up
# trigger the svg convertion
curl http://localhost:5000/image
```

For details, check out the `src/Dockerfile`.

# Run the Script on Ubuntu

```bash
# pip install pipenv
pipenv install
pipenv shell
cd src
python svg-converter-service.py
```

That should do the trick (tested on Ubuntu 17.04). However, somehow I used to get the error `ModuleNotFoundError: No module named 'PIL'`.

- Fix a) add the dependency `Pillow = "==5.1.0"` to the Pipfile
- or Fix b) 

```bash
sudo apt-get install python3-dev python3-setuptools
sudo apt-get install python3-dev python3-setuptools libjpeg8-dev zlib1g-dev libfreetype6-dev liblcms2-dev libwebp-dev tcl8.5-dev tk8.5-dev
```

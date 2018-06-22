# Alpine Image with CairoSVG

see `src/Dockerfile`.

# Run the Script on Ubuntu

```bash
pipenv install
pipenv shell
python src/svg-converter-service.py
```

You may get the error `ModuleNotFoundError: No module named 'PIL'`.

- Fix a) add the dependency `Pillow = "==5.1.0"` to the Pipfile
- or Fix b) 

```bash
sudo apt-get install python3-dev python3-setuptools
sudo apt-get install libjpeg8-dev zlib1g-dev libfreetype6-dev liblcms2-dev libwebp-dev tcl8.5-dev tk8.5-dev
```

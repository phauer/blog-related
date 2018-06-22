import cairosvg
from flask import Flask, Response

app = Flask(__name__)

@app.route('/image')
def convert_image():
    png_data = cairosvg.svg2png(url="circle.svg", parent_width=300, parent_height=300)
    return Response(png_data, mimetype='image/png')

if __name__ == '__main__':
    app.run(debug=True, port=5000, host='0.0.0.0')
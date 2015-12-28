rem local mongodb installation needed

echo "Creating dbpath"
mkdir \data\db\

echo "Starting MongoDB..."
start mongod 

echo "Starting MongoDB Client..."
start mongo
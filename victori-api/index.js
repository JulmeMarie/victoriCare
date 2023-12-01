const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 3001;

//app.use(express.json());

app.use(cors());
app.use(bodyParser.urlencoded({ extended : false}));
app.use(bodyParser.json());

app.get('/', (request, response) => {
    response.send("Hello World from express");
})

app.get('/status',(request, response) => {
    const status = {
        "status" : "Running"
    };

    response.send(status);
});

app.listen(PORT, () => {
    console.log("Server listenning on port : ", PORT);
});
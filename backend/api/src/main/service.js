const express = require('express');
const cors = require('cors');

const app = express();

// Configurar cors
app.use(cors({
  origin: 'http://localhost:3000', // Reemplaza con la URL de tu aplicación de React
}));

// Resto de la configuración del servidor...

app.listen(8080, () => {
  console.log('Servidor escuchando en el puerto 8080');
});
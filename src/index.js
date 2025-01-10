import express from 'express';
import cors from 'cors';
import foodRoutes from './routes/foodRoutes.js';
import reservationRoutes from './routes/reservationRoutes.js';

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.use('/api/foods', foodRoutes);
app.use('/api/reservations', reservationRoutes);

// Global error handler
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({
    message: 'An unexpected error occurred'
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
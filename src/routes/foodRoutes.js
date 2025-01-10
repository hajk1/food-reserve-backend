import express from 'express';
import { FoodService } from '../services/foodService.js';
import { validateDate } from '../middleware/validators.js';

const router = express.Router();
const foodService = new FoodService();

router.get('/', validateDate, (req, res) => {
  const foods = foodService.getAvailableFoods(req.query.date);
  res.json(foods);
});

export default router;
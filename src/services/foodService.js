import { Food } from '../models/Food.js';
import { FoodCategory } from '../constants/enums.js';

export class FoodService {
  getAvailableFoods(date) {
    // Simulated data - in a real app, this would come from a database
    return [
      new Food('1', 'Spaghetti Carbonara', 'Classic Italian pasta dish', FoodCategory.MAIN_COURSE, true),
      new Food('2', 'Caesar Salad', 'Fresh romaine lettuce with Caesar dressing', FoodCategory.SALAD, true)
    ];
  }
}
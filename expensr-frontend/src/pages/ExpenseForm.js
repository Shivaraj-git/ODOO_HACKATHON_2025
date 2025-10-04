import React, { useState } from 'react';
import { TextField, Button, Box, Typography, Alert } from '@mui/material';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function ExpenseForm() {
  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    if (!description || !amount || !category) {
      setError('Please fill all fields');
      return;
    }
    try {
      await api.post('/expenses', { description, amount: parseFloat(amount), category });
      navigate('/dashboard');
    } catch {
      setError('Failed to save expense');
    }
  }

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto' }}>
      <Typography variant="h4" align="center" gutterBottom>Add Expense</Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      <form onSubmit={handleSubmit} noValidate>
        <TextField
          fullWidth label="Description"
          value={description} required
          onChange={e => setDescription(e.target.value)}
          margin="normal"
        />
        <TextField
          fullWidth label="Amount"
          type="number"
          value={amount} required
          onChange={e => setAmount(e.target.value)}
          margin="normal"
          inputProps={{ step: "0.01" }}
        />
        <TextField
          fullWidth label="Category"
          value={category} required
          onChange={e => setCategory(e.target.value)}
          margin="normal"
        />
        <Button fullWidth variant="contained" sx={{ mt: 3 }} type="submit">Save</Button>
      </form>
    </Box>
  );
}

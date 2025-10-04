import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Alert } from '@mui/material';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function Signup() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    try {
      await api.post('/auth/signup', { email, password, name });
      navigate('/login');
    } catch {
      setError('Signup error');
    }
  }

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto' }}>
      <Typography variant="h4" align="center" gutterBottom>
        Signup
      </Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      <form onSubmit={handleSubmit} noValidate>
        <TextField
          fullWidth
          label="Name"
          margin="normal"
          required
          value={name}
          onChange={e => setName(e.target.value)}
        />
        <TextField
          fullWidth
          label="Email"
          margin="normal"
          required
          value={email}
          onChange={e => setEmail(e.target.value)}
          type="email"
        />
        <TextField
          fullWidth
          label="Password"
          margin="normal"
          required
          value={password}
          onChange={e => setPassword(e.target.value)}
          type="password"
        />
        <Button fullWidth variant="contained" sx={{ mt: 3 }} type="submit">
          Signup
        </Button>
      </form>
    </Box>
  );
}

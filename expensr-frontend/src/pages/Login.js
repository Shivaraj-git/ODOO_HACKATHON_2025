import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Alert, Link } from '@mui/material';
import api from '../services/api';
import { useNavigate, Link as RouterLink } from 'react-router-dom';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    try {
      const res = await api.post('/auth/login', { email, password });
      localStorage.setItem('jwtToken', res.data.token);
      navigate('/dashboard');
    } catch {
      setError('Invalid email or password');
    }
  }

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto' }}>
      <Typography variant="h4" align="center" gutterBottom>
        Login
      </Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      <form onSubmit={handleSubmit} noValidate>
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
        <Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} type="submit">
          Login
        </Button>
      </form>
      <Typography variant="body2" align="center">
        Don't have an account?{' '}
        <Link component={RouterLink} to="/signup">
          Sign Up
        </Link>
      </Typography>
    </Box>
  );
}

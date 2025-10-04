import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Dashboard from './pages/Dashboard';
import ExpenseForm from './pages/ExpenseForm';
import { Container, CssBaseline } from '@mui/material';

function RequireAuth({ children }) {
  const token = localStorage.getItem('jwtToken');
  return token ? children : <Navigate to="/login" />;
}

export default function App() {
  return (
    <>
      <CssBaseline />
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/dashboard" element={<RequireAuth><Dashboard /></RequireAuth>} />
          <Route path="/expense" element={<RequireAuth><ExpenseForm /></RequireAuth>} />
          <Route path="*" element={<Navigate to="/dashboard" />} />
        </Routes>
      </Container>
    </>
  );
}

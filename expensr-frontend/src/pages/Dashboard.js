import React, { useEffect, useState } from 'react';
import { Typography, Paper, List, ListItem, ListItemText, Stack } from '@mui/material';
import api from '../services/api';

export default function Dashboard() {
  const [summary, setSummary] = useState(null);

  useEffect(() => {
    api.get('/dashboard/summary')
      .then(res => setSummary(res.data))
      .catch(console.error);
  }, []);

  if (!summary) return <Typography>Loading...</Typography>;

  return (
    <>
      <Typography variant="h4" mb={3}>Dashboard Summary</Typography>
      <Stack direction="row" spacing={4} mb={4}>
        <Paper sx={{ p: 3, flex: 1 }}>
          <Typography variant="h6">Total Expenses</Typography>
          <Typography variant="h5">{summary.totalExpenses}</Typography>
        </Paper>
        <Paper sx={{ p: 3, flex: 1 }}>
          <Typography variant="h6">Total Amount</Typography>
          <Typography variant="h5">${summary.totalAmount.toFixed(2)}</Typography>
        </Paper>
        <Paper sx={{ p: 3, flex: 1 }}>
          <Typography variant="h6">Average Expense</Typography>
          <Typography variant="h5">${summary.averageExpense.toFixed(2)}</Typography>
        </Paper>
      </Stack>
      <Typography variant="h5" mb={2}>Recent Expenses</Typography>
      <Paper sx={{ p: 2 }}>
        <List>
          {summary.recentExpenses.map(e =>
            <ListItem key={e.id} divider>
              <ListItemText primary={e.description} secondary={`$${e.amount.toFixed(2)}`} />
            </ListItem>
          )}
        </List>
      </Paper>
    </>
  );
}

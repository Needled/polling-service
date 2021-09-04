import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/Add a new service/i);
  expect(linkElement).toBeInTheDocument();
    const linkElement2 = screen.getByText(/Registered services/i);
  expect(linkElement).toBeInTheDocument();
});

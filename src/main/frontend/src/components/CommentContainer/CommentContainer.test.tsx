import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import CommentContainer from './CommentContainer';

describe('<CommentContainer />', () => {
  test('it should mount', () => {
    render(<CommentContainer />);

    const commentList = screen.getByTestId('CommentContainer');

    expect(commentList).toBeInTheDocument();
  });
});
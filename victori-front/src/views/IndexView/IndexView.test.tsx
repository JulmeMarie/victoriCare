import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import IndexView from './IndexView';
import { CONTENTSNAME } from '../../utils/Constants';

describe('<IndexView />', () => {
  test('it should mount', () => {
    render(<IndexView formName={CONTENTSNAME.LOGIN_OWNER} />);

    const indexView = screen.getByTestId('IndexView');

    expect(indexView).toBeInTheDocument();
  });
});
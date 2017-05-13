import React from 'react';
import {render} from 'react-dom';
import App from './App';
import './index.css';
import {Provider} from 'react-redux';
import {createStore} from 'redux';
import rootReducer from './rootReducer';

const store = (window.devToolsExtension ? window.devToolsExtension()(createStore) : createStore)(rootReducer);

render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root')
);

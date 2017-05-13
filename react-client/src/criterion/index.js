/**
 * Created by vigi on 5/13/2017 4:38 PM.
 */
import {post} from '../restClient';

export const types = {
    SUBMIT_CRITERION_DEFINITION: 'SUBMIT_CRITERION_DEFINITION',
    RECEIVED_VALUES: 'RECEIVED_VALUES',
    IS_SUBMITTING: 'IS_SUBMITTING'
};

const initialCriteria = require('./criteria.json');

export const actions = {
    submitCriteria: (criterion) => {
        return (dispatch) => {
            post('/api/criteria', criterion)
                .then(response => {
                    if (response.ok) {
                        return response;
                    }
                    const error = new Error(`HTTP Error ${response.statusText}`);
                    error.status = response.statusText;
                    error.response = response;
                    error.id = new Date().getTime();
                    throw new Error({message: 'Error', _error: error});
                }).then(response => {
                return response.json();
            }).then((values) => {
                dispatch(actions.receiveValues(values));
            });
        };
    },
    receiveValues: (values) => ({
        type: types.IS_SUBMITTING,
        payload: values
    }),
};

export function reducer(state = {initialCriteria}, action = {}) {
    switch (action.type) {
        case types.RECEIVED_VALUES:
            return {...state, values: action.payload};
        default:
            return state;
    }
}


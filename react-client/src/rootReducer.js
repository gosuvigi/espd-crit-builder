/**
 * Created by vigi on 5/11/2017 8:31 PM.
 */
import {combineReducers} from 'redux';
import {reducer as reduxFormReducer} from 'redux-form';
import {reducer as criteriaReducer} from './criterion/index';

const rootReducer = combineReducers({
    criteria: criteriaReducer,
    form: reduxFormReducer, // mounted under "form"
});

export default rootReducer;
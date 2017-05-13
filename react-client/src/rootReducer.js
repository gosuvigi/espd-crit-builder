/**
 * Created by vigi on 5/11/2017 8:31 PM.
 */
import {combineReducers} from 'redux';
import { reducer as reduxFormReducer } from 'redux-form';

function criteria(state = {}, action) {
    return state;
}

const rootReducer = combineReducers({
    criteria,
    form: reduxFormReducer, // mounted under "form"
});

export default rootReducer;
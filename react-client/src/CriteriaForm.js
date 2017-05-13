/**
 * Created by vigi on 5/11/2017 8:38 PM.
 */
import React from 'react';
import {reduxForm} from 'redux-form';
import Criterion from './Criterion';

const CriteriaForm = props => {
    const {handleSubmit, pristine, reset, submitting} = props;
    return (
        <form onSubmit={handleSubmit} className="criteriaForm">
            <div className="container-fluid">
                <div className="row">
                    <Criterion/>
                </div>
                <div className="row">
                    <button type="submit" disabled={submitting} className="btn btn-primary">Submit</button>
                    <button type="button" disabled={pristine || submitting} onClick={reset}>
                        Clear Values
                    </button>
                </div>
            </div>
        </form>
    )
};

export default reduxForm({
    form: 'criteriaForm', // a unique identifier for this form
})(CriteriaForm);


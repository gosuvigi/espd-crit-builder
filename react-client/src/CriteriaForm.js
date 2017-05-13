/**
 * Created by vigi on 5/11/2017 8:38 PM.
 */
import React from 'react';
import {Field, FieldArray, reduxForm} from 'redux-form';

const renderField = ({input, label, type, meta: {touched, error}}) => (
    <div>
        <label>{label}</label>
        <div>
            <input {...input} type={type} placeholder={label} />
            {touched && error && <span>{error}</span>}
        </div>
    </div>
);

const renderSingleRequirement = (requirement, index, fields) => (
    <li key={index}>
        <button
            type="button" className="btn btn-danger glyphicon-trash"
            title="Remove Requirement"
            onClick={() => fields.remove(index)}
        />
        <Field
            name={`${requirement}.name`}
            type="text"
            component={renderField}
            label={`Requirement #${index + 1}`}
        />
    </li>
);

const renderRequirements = ({fields, meta: {error}}) => (
    <ul>
        <li>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
        </li>
        {fields.map(renderSingleRequirement)}
        {error && <li className="error">{error}</li>}
    </ul>
);

const renderSingleRequirementGroup = (reqGroup, index, fields) => (
    <li key={index}>
        <button
            type="button" className="btn btn-danger glyphicon-trash"
            title="Remove Requirement Group"
            onClick={() => fields.remove(index)}
        />
        <h4>Member #{index + 1}</h4>
        <Field
            name={`${reqGroup}.name`}
            type="text"
            component={renderField}
            label="Group Name"
        />
        <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements} />
        <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups} />
    </li>
);

const renderRequirementGroups = ({fields, meta: {error, submitFailed}}) => (
    <ul>
        <li>
            <button type="button" onClick={() => fields.push({})}>Add Requirement Group</button>
            {submitFailed && error && <span>{error}</span>}
        </li>
        {fields.map(renderSingleRequirementGroup)}
    </ul>
);

const FieldArraysForm = props => {
    const {handleSubmit, pristine, reset, submitting} = props;
    return (
        <form onSubmit={handleSubmit}>
            <Field
                name="criterionName"
                type="text"
                component={renderField}
                label="Criterion Name"
            />
            <FieldArray name="requirementGroups" component={renderRequirementGroups} />
            <div>
                <button type="submit" disabled={submitting} className="btn btn-primary">Submit</button>
                <button type="button" disabled={pristine || submitting} onClick={reset}>
                    Clear Values
                </button>
            </div>
        </form>
    )
};

export default reduxForm({
    form: 'criteriaForm', // a unique identifier for this form
})(FieldArraysForm);


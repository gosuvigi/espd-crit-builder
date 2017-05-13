/**
 * Created by vigi on 5/11/2017 8:37 PM.
 */
import React from 'react';
import {Field, FieldArray} from 'redux-form';

const renderField = ({input, label, type, meta: {touched, error}}) => (
    <div>
        <label>{label}</label>
        <div>
            <input {...input} type={type} placeholder={label}/>
            {touched && error && <span>{error}</span>}
        </div>
    </div>
);

const renderSingleRequirement = (requirement, index, fields) => (
    <li key={index} className="list-group-item list-group-item-success">
        <button
            type="button" className="btn btn-danger glyphicon glyphicon-trash"
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
    <ul className="list-group">
        <li>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
        </li>
        {fields.map(renderSingleRequirement)}
        {error && <li className="error">{error}</li>}
    </ul>
);

const renderSingleRequirementGroup = (reqGroup, index, fields) => (
    <li key={index} className="list-group-item list-group-item-warning">
        <button
            type="button" className="btn btn-danger glyphicon glyphicon-trash"
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
        <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements}/>
        <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups}/>
    </li>
);

const renderRequirementGroups = ({fields, meta: {error, submitFailed}}) => (
    <ul className="list-group">
        <li>
            <button type="button" onClick={() => fields.push({})}>Add Requirement Group</button>
            {submitFailed && error && <span>{error}</span>}
        </li>
        {fields.map(renderSingleRequirementGroup)}
    </ul>
);

const Criterion = () => {
    return (
        <div className="criterion col-md-12">
            <div className="row pull-left">
                <div className="col-md-12">
                    <Field
                        name="name"
                        type="text"
                        component={renderField}
                        label="Criterion Name"
                    />
                </div>
                <div className="col-md-11 col-md-offset-1">
                    <FieldArray name="requirementGroups" component={renderRequirementGroups}/>
                </div>
            </div>
        </div>
    )
};

export default Criterion;

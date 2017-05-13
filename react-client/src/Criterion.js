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

const renderSingleRequirement = (requirement, index, fields, nesting) => (
    <li key={index} className="requirement list-group-item list-group-item-success">
        <button
            type="button" className="btn btn-danger glyphicon glyphicon-trash"
            title="Remove Requirement"
            onClick={() => fields.remove(index)}
        />
        <Field
            name={`${requirement}.name`}
            type="text"
            component={renderField}
            label={`Requirement #${index + 1} nesting ${nesting}`}
        />
    </li>
);

const renderRequirements = ({fields, meta: {error}, nesting}) => (
    <ul className="requirements list-group">
        <li>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
        </li>
        {
            fields.map((requirement, index, fields) => renderSingleRequirement(requirement, index, fields, nesting + 1))
        }
        {error && <li className="error">{error}</li>}
    </ul>
);

const renderSingleRequirementGroup = (reqGroup, index, fields, nesting) => (
    <li key={index} className="requirement-group list-group-item list-group-item-warning">
        <button
            type="button" className="btn btn-danger glyphicon glyphicon-trash"
            title="Remove Requirement Group"
            onClick={() => fields.remove(index)}
        />
        <h4>Member #{index + 1} nesting: {nesting}</h4>
        <Field
            name={`${reqGroup}.name`}
            type="text"
            component={renderField}
            label="Group Name"
        />
        <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements} nesting={nesting}/>
        <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups} nesting={nesting}/>
    </li>
);

const renderRequirementGroups = ({fields, meta: {error, submitFailed}, nesting}) => (
    <ul className="requirement-groups list-group">
        <li>
            <button type="button" onClick={() => fields.push({})}>Add Requirement Group {nesting}</button>
            {submitFailed && error && <span>{error}</span>}
        </li>
        {
            fields.map((reqGroup, index, fields) => (renderSingleRequirementGroup(reqGroup, index, fields, nesting + 1)))
        }
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
                    <FieldArray name="requirementGroups" component={renderRequirementGroups} nesting={0}/>
                </div>
            </div>
        </div>
    )
};

export default Criterion;

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

const renderSingleRequirement = (requirement, index, fields, identifier) => {
    const newIdentifier = identifier + "." + (index + 1);
    return (
        <div key={index} className="requirement list-group-item list-group-item-success">
            <div className="row">
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.name`}
                        type="text"
                        component={renderField}
                        label={`Req #${newIdentifier} name`}
                    />
                </div>
                <div className="col-md-3">
                    <label>Type</label>
                    <div>
                        <Field
                            name={`${requirement}.type`}
                            component="select"
                            label="Requirement Type"
                        >
                            <option>--Select--</option>
                            <option value="DESCRIPTION">DESCRIPTION</option>
                            <option value="AMOUNT">AMOUNT</option>
                            <option value="DATE">DATE</option>
                            <option value="NUMBER">NUMBER</option>
                        </Field>
                    </div>
                </div>
                <div className="col-md-3">
                    <Field
                        name={`${requirement}.value`}
                        type="text"
                        component={renderField}
                        label="Value"
                    />
                </div>
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-success glyphicon glyphicon-trash"
                        title="Remove Requirement"
                        onClick={() => fields.remove(index)}
                    />
                </div>
            </div>
        </div>
    )
};

const renderRequirements = ({fields, meta: {error}, identifier, nesting}) => (
    <div className="requirements list-group text-left">
        {
            fields.map((requirement, index, fields) => renderSingleRequirement(requirement, index, fields, identifier))
        }
        {error && <li className="error">{error}</li>}
        <div>
            <button type="button" onClick={() => fields.push()}>Add Requirement</button>
        </div>
    </div>
);

const renderSingleRequirementGroup = (reqGroup, index, fields, identifier, nesting) => {
    const newIdentifier = !identifier ? (index + 1) : identifier + "." + (index + 1);
    return (
        <div key={index} className="requirement-group list-group-item list-group-item-warning text-left">
            <div className="row">
                <div className="col-md-3">
                    <h4>Requirement group #{newIdentifier}</h4>
                </div>
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-danger glyphicon glyphicon-trash"
                        title="Remove Requirement Group"
                        onClick={() => fields.remove(index)}
                    />
                </div>
                <div className="col-md-1">
                    <label htmlFor="visible">Show/hide</label>
                    <div>
                        <Field
                            name={`${reqGroup}.visible`}
                            id="visible"
                            component="input"
                            type="checkbox"
                        />
                    </div>
                </div>
                <div className="col-md-1">
                    <button
                        type="button" className="btn btn-danger glyphicon glyphicon-arrow-down"
                        title="Show/hide"
                        data-toggle="collapse" data-target="#demo"
                    />
                </div>
            </div>
            <div className="row">
                <Field
                    name={`${reqGroup}.name`}
                    type="text"
                    component={renderField}
                    label="Group Name"
                    className="text-left"
                />
                <FieldArray name={`${reqGroup}.requirements`} component={renderRequirements} identifier={newIdentifier}
                            nesting={nesting + 1}/>
                <FieldArray name={`${reqGroup}.requirementGroups`} component={renderRequirementGroups}
                            identifier={newIdentifier} nesting={nesting + 1}/>
            </div>
        </div>
    )
};

const renderRequirementGroups = ({fields, meta: {error, submitFailed}, identifier, nesting}) => {
    const newName = !identifier ? "G" + (fields.length + 1) : "G" + identifier + "." + (fields.length + 1);
    return (
        <div className="row">
            <div className={`col-md-11 col-md-offset-1`}>
                <div className="requirement-groups list-group text-left">
                    {
                        fields.map((reqGroup, index, fields) => (renderSingleRequirementGroup(reqGroup, index, fields, identifier, nesting)))
                    }
                    <div>
                        <button type="button" onClick={() => fields.push({name: newName})}>Add Requirement Group
                        </button>
                        {submitFailed && error && <span>{error}</span>}
                    </div>
                </div>
            </div>
        </div>
    )
};

const EditCriterion = () => {
    return (
        <div className="criterion col-md-12 bg-info">
            <div className="row">
                <div className="col-md-4 text-left">
                    <Field
                        name="name"
                        type="text"
                        component={renderField}
                        label="Criterion Name"
                    />
                </div>
                <div className="col-md-11 col-md-offset-1">
                    <FieldArray name="requirementGroups" component={renderRequirementGroups} identifier="" nesting={1}/>
                </div>
            </div>
        </div>
    )
};

export default EditCriterion;

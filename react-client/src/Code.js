/**
 * Created by vigi on 6/24/2017 1:49 PM.
 */
import React from 'react';

const Code = ({ source, language }) =>
    <div><pre>{source}</pre></div>

Code.defaultProps = {
    language: 'js'
};

export default Code;

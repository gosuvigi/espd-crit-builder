/**
 * Created by vigi on 6/24/2017 1:49 PM.
 */
import React from 'react';
// import Markdown from './Markdown';

const Code = ({ source, language }) =>
    <div>{source}</div>
    {/*<Markdown content={'```' + language + source + '```'}/>;*/}


Code.defaultProps = {
    language: 'js'
};

export default Code;

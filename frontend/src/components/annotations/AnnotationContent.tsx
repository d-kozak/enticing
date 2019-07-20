import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {CorpusFormat, EntityInfo} from "../../entities/CorpusFormat";
import {Entity, Word} from "./NewAnnotatedText";
import Divider from "@material-ui/core/es/Divider";

const styles = createStyles({
    image: {
        margin: '5px',
        maxWidth: '150px',
        maxHeight: '150px',
    },
    attributeName: {
        fontWeight: 'bold'
    }
});

export interface AnnotationContentProps extends WithStyles<typeof styles> {
    corpusFormat: CorpusFormat,
    word: Word,
    enclosingEntity?: Entity
}

const splitUrls = (maybeMultipleUrls: string): Array<string> => {
    if (maybeMultipleUrls.indexOf("|") != -1) {
        return maybeMultipleUrls.split("|");
    }
    return [maybeMultipleUrls]
}

const EntityComponent = ({data, entityInfo, classes}: { data: Entity, entityInfo: EntityInfo, classes: any }) => {
    const attributeNames = Object.keys(entityInfo.attributes);
    const imageIndex = attributeNames.indexOf("image")
    return <div>
        <Typography variant="h6">Entity info</Typography>
        {imageIndex >= 0 && imageIndex < data.attributes.length && splitUrls(data.attributes[imageIndex]).map((url, index) =>
            <img key={index} className={classes.image} src={url}/>)}
        <Typography><b>nertag : </b>{data.entityClass}</Typography>
        {data.attributes
            .map((value, i) => ([value, attributeNames[i]]))
            .filter(([, name]) => name !== "image")
            .map(([attributeValue, name]) => <React.Fragment key={name}>
                <Typography variant="body1"><b>{name}</b> : {name == 'url' ?
                    <a href={attributeValue}>{attributeValue}</a> : attributeValue}</Typography>
            </React.Fragment>)}
    </div>
}

const AnnotationContent = (props: AnnotationContentProps) => {
    const {classes, word, enclosingEntity: entityData, corpusFormat} = props;
    const entityInfo = entityData && corpusFormat.entities[entityData.entityClass]
    if (entityData && !entityInfo) {
        console.warn("no entity info found for entity " + entityData.entityClass + ", skipping it's content")
    }
    const indexNames = Object.keys(corpusFormat.indexes);

    return <div>
        {entityInfo && <EntityComponent data={entityData!} entityInfo={entityInfo} classes={classes}/>}
        {entityInfo && <Divider/>}
        {entityInfo && word.indexes.length > 1 && <Typography variant="h6">Word info</Typography>}
        {word.indexes
            .map((value, i) => ([value, indexNames[i]]) as [string, string])
            .filter(([, name]) => name !== "token")
            .map(
                ([indexValue, indexName]) => <div key={indexName}>
                    <Typography variant="body1"><span
                        className={classes.attributeName}>{indexName} : </span>{indexValue}</Typography>
                </div>
            )}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotationContent)
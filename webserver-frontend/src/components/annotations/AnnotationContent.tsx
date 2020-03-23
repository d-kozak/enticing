import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {CorpusFormat, EntityInfo} from "../../entities/CorpusFormat";
import {Entity, Word} from "./TextUnitList";
import Grid from "@material-ui/core/es/Grid";

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
    enclosingEntity?: Entity,
    queryMatch?: string,
}

const splitUrls = (maybeMultipleUrls: string): Array<string> => {
    if (maybeMultipleUrls.indexOf("|") != -1) {
        return maybeMultipleUrls.split("|");
    }
    return [maybeMultipleUrls]
};

const QueryMatchComponent = ({match}: { match: string }) => {
    return <div>
        <Typography variant="h6">Matches: {match}</Typography>
    </div>;
};

const EntityComponent = ({data, entityInfo, classes}: { data: Entity, entityInfo: EntityInfo, classes: any }) => {
    const attributeNames = Object.keys(entityInfo.attributes);
    const imageIndex = attributeNames.indexOf("image");
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

export const WordComponent = ({word, indexNames, classes, defaultIndex}: { word: Word, indexNames: Array<string>, defaultIndex: string, classes: any }) => {
    const indexes: Array<[string, string]> = word.indexes
        .map((value, i) => ([value, indexNames[i]]) as [string, string])
        .filter(([, name]) => name !== defaultIndex);
    const split: Array<Array<[string, string]>> = indexes.length < 6 ? [indexes] : [indexes.slice(0, indexes.length / 2), indexes.slice(indexes.length / 2, indexes.length)]
    return <React.Fragment>
        <Typography variant="h6">Word info</Typography>
        <Grid container direction="row">
            {split.map((column, i) =>
                <Grid key={i} item>
                    {column.map(
                        ([indexValue, indexName]) => <div key={indexName}>
                            <Typography variant="body1"><span
                                className={classes.attributeName}>{indexName} : </span>{indexValue}</Typography>
                        </div>
                    )}
                </Grid>
            )}
        </Grid>
    </React.Fragment>
}

const AnnotationContent = (props: AnnotationContentProps) => {
    const {classes, word, enclosingEntity: entityData, queryMatch, corpusFormat} = props;
    const entityInfo = entityData && corpusFormat.entities[entityData.entityClass];
    if (entityData && !entityInfo) {
        console.warn("no entity info found for entity " + entityData.entityClass + ", skipping it's content")
    }
    const indexNames = Object.keys(corpusFormat.indexes);
    const defaultIndex = corpusFormat.defaultIndex || "token";

    return <Grid direction="row" container>
        {queryMatch && <Grid item> <QueryMatchComponent match={queryMatch}/> </Grid>}
        {entityInfo &&
        <Grid item> <EntityComponent data={entityData!} entityInfo={entityInfo} classes={classes}/> </Grid>}
        <Grid item> <WordComponent word={word} indexNames={indexNames} defaultIndex={defaultIndex} classes={classes}/>
        </Grid>
    </Grid>
};

export default withStyles(styles, {
    withTheme: true
})(AnnotationContent)
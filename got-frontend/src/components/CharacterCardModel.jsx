import { Clear, Favorite } from "@mui/icons-material";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Grid,
  IconButton,
  Modal,
  Typography,
} from "@mui/material";
import React from "react";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  boxShadow: 24,
  p: 1,
};

const CharacterCardModel = ({
  character,
  open,
  handleClose,
  handleFavorite,
}) => {
  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Card sx={{ maxWidth: 345 }}>
          <CardHeader
            avatar={
              <Avatar
                alt={character?.characterName}
                src={
                  character?.characterImageUrl
                    ? character?.characterImageUrl
                    : "default"
                }
              />
            }
            action={
              <IconButton onClick={handleClose}>
                <Clear />
              </IconButton>
            }
            title={character.characterName}
            subheader={
              character.actorName ? "Actor Name : " + character.actorName : ""
            }
          />
          <CardContent>
            <Grid container spacing={1}>
              <Grid item xs={12}>
                <Box sx={{ display: "flex", alignItems: "center" }}>
                  <Typography variant="body2" sx={{ marginRight: 2 }}>
                    House :
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {character?.houses?.toString()}
                  </Typography>
                </Box>
              </Grid>
              {character?.killed?.length !== 0 && (
                <Grid item xs={12}>
                  <Box sx={{ display: "flex", alignItems: "center" }}>
                    <Typography variant="body2" sx={{ marginRight: 2 }}>
                      Killed:
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {character?.killed?.toString()}
                    </Typography>
                  </Box>
                </Grid>
              )}

              {character?.killedBy?.length !== 0 && (
                <Grid item xs={12}>
                  <Box sx={{ display: "flex", alignItems: "center" }}>
                    <Typography variant="body2" sx={{ marginRight: 2 }}>
                      KilledBy:
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {character?.killedBy?.toString()}
                    </Typography>
                  </Box>
                </Grid>
              )}
            </Grid>
          </CardContent>
          <CardActions disableSpacing>
            <IconButton
              aria-label="add to favorites"
              onClick={() => handleFavorite(character.id)}
            >
              <Favorite color={character?.favorite ? "error" : ""} />
            </IconButton>
          </CardActions>
        </Card>
      </Box>
    </Modal>
  );
};

export default CharacterCardModel;

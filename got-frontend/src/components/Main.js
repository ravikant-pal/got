import { Autocomplete, Box, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import Tree from "react-d3-tree";
import CharacterCardModel from "./CharacterCardModel";
import renderForeignObjectNode from "./CustomeNode";
import { useCenteredTree } from "./helper";

const nodeSize = { x: 200, y: 200 };
const foreignObjectProps = {
  width: nodeSize.x,
  height: nodeSize.y,
  x: -20,
  y: -20,
};

const URLS = {
  baseUrl: "http://localhost:8080/api/characters/",
  house: "houses",
  familyTree: "family-tree/",
  favourite: "/favourite",
};
const Main = () => {
  const [translate, containerRef] = useCenteredTree();
  const [hierarchy, setHierarchy] = useState({});
  const [character, setCharacter] = useState({});
  const [houses, setHouses] = useState([]);
  const [open, setOpen] = useState(false);

  const handleFavorite = (id) => {
    const makeFavorite = async () => {
      try {
        const response = await axios.put(URLS.baseUrl + id + URLS.favourite);
        setCharacter(response.data);
      } catch (error) {
        console.error("Error fetching houses:", error);
      }
    };
    makeFavorite();
  };

  const onNodeClick = (id) => {
    const fetchById = async () => {
      try {
        const response = await axios.get(URLS.baseUrl + id);
        setCharacter(response.data);
        setOpen(true);
      } catch (error) {
        console.error("Error fetching houses:", error);
      }
    };
    fetchById();
  };

  const handleChange = (event, selectedHouse) => {
    const fetchCharacters = async () => {
      try {
        const response = await axios.get(
          URLS.baseUrl + URLS.familyTree + selectedHouse
        );
        setHierarchy(response.data);
      } catch (error) {
        console.error("Error fetching houses:", error);
      }
    };
    if (selectedHouse) {
      fetchCharacters();
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(URLS.baseUrl + URLS.house);
        setHouses(response.data.map((h) => h.name));
      } catch (error) {
        console.error("Error fetching houses:", error);
      }
    };

    fetchData();
  }, []);

  const handleClose = () => setOpen(false);

  return (
    <Box sx={{ width: "100vw", height: "100vh" }} ref={containerRef}>
      <CharacterCardModel
        character={character}
        open={open}
        handleClose={handleClose}
        handleFavorite={handleFavorite}
      />
      {Object.keys(hierarchy).length !== 0 && (
        <Tree
          data={hierarchy}
          renderCustomNodeElement={(rd3tProps) =>
            renderForeignObjectNode({
              ...rd3tProps,
              foreignObjectProps,
              onNodeClick,
            })
          }
          translate={translate}
          orientation="vertical"
        />
      )}

      <Autocomplete
        disablePortal
        size="small"
        options={houses}
        onChange={handleChange}
        sx={{
          top: "5%",
          zIndex: 1,
          width: 400,
          left: "50%",
          transform: "translateX(-50%)",
          maxWidth: "30%",
          borderRadius: 2,
          position: "absolute",
          backgroundColor: "#FFF",
        }}
        renderInput={(params) => <TextField {...params} label="Select House" />}
      />
    </Box>
  );
};

export default Main;

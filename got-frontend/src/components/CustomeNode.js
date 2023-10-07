import { Avatar, Box, Typography } from "@mui/material";

const renderForeignObjectNode = ({
  nodeDatum,
  toggleNode,
  foreignObjectProps,
  onNodeClick,
}) => (
  <g>
    <foreignObject {...foreignObjectProps}>
      <Box display="flex" alignItems="center">
        <Avatar
          alt={nodeDatum.name}
          src={
            nodeDatum.attributes?.imageUrl
              ? nodeDatum.attributes?.imageUrl
              : "default"
          }
          onClick={() => onNodeClick(nodeDatum.attributes?.id)}
        />

        <Typography
          variant="body2"
          sx={{
            marginLeft: "3%",
          }}
        >
          {nodeDatum.name}
        </Typography>
      </Box>
    </foreignObject>
  </g>
);

export default renderForeignObjectNode;

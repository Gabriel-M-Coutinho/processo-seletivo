import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'standalone',
  reactStrictMode: true,


  experimental: {

    serverActions: {
      bodySizeLimit: '2mb'
    },

    optimizePackageImports: ['@mui/material']
  }
}

export default nextConfig;

# Stage 1: Build
FROM node:20-alpine AS builder
WORKDIR /app

# Instala dependências necessárias para SWC
RUN apk add --no-cache libc6-compat

# Copia arquivos de configuração
COPY package.json package-lock.json ./
COPY tsconfig.json ./
COPY next-env.d.ts ./

# Instala dependências
RUN npm ci

# Copia o restante dos arquivos
COPY . .

# Gera o build
RUN npm run build

# Stage 2: Production
FROM node:20-alpine AS runner
WORKDIR /app

ENV NODE_ENV production
ENV NEXT_TELEMETRY_DISABLED 1

# Copia os arquivos necessários
COPY --from=builder /app/package.json ./
COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

RUN npm install --omit=dev

EXPOSE 3000
CMD ["node", "server.js"]